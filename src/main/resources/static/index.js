function showDiv(id) {
    const divs = document.getElementsByTagName('div');
    for (let i = 0; i < divs.length; i++) {
        if (divs[i].id === 'checkSpam' || divs[i].id === 'train') {
            divs[i].style.display = 'none';
        }
    }
    document.getElementById(id).style.display = 'block';
}

window.onload = function () {
    showDiv('checkSpam');
}

document.addEventListener('DOMContentLoaded', (event) => {
    document.querySelector('#checkSpam input[type="submit"]').addEventListener('click', function (event) {
        event.preventDefault();

        const content = document.querySelector('#checkSpam textarea[name="content"]').value;
        const resultDiv = document.getElementById('spamCheckResult');

        fetch('/check-spam', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({content: content}),
        })
            .then(response => response.json())
            .then(data => {
                resultDiv.innerHTML = '';

                const messageDiv = document.createElement('div');
                const spamProbabilityDiv = document.createElement('div');

                messageDiv.classList.add('result-message');
                spamProbabilityDiv.classList.add('result-spamProbability');

                messageDiv.textContent = 'Message: ' + data.message;
                spamProbabilityDiv.textContent = 'Spam Probability: ' + data.spamProbability;

                resultDiv.appendChild(messageDiv);
                resultDiv.appendChild(spamProbabilityDiv);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    });

    document.querySelector('#train form').addEventListener('submit', function (event) {
        event.preventDefault();

        const contentTextArea = document.querySelector('#train textarea[name="content"]');

        const content = contentTextArea.value;

        fetch('/train-spam', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                content: content,
            }),
        }).then(function(response) {
            if (response.status === 200) {
                contentTextArea.value = '';

                const resultDiv = document.getElementById('trainResult');
                resultDiv.innerHTML = '';

                const messageDiv = document.createElement('div');
                messageDiv.textContent = 'Training was successful';
                resultDiv.appendChild(messageDiv);
            }
        })
            .catch((error) => {
                console.error('Error:', error);
            });
    });
});
